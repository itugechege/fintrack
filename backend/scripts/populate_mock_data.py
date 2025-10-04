#!/usr/bin/env python3
"""
populate_users_copy.py

Generates a large number of users in parallel (multiprocessing),
writes per-worker CSV files, and then bulk-loads them into Postgres
using COPY for maximum speed.

Windows-safe: uses if __name__ == '__main__' guard.
"""

import csv
import os
import tempfile
import shutil
from multiprocessing import Pool, cpu_count
from math import ceil
from random import randint, choice
from datetime import datetime
from faker import Faker
import psycopg2
from psycopg2 import sql
from tqdm import tqdm

# ---------- CONFIG ----------
DB_CONFIG = {
    'dbname': 'dev_db',
    'user': 'admin',
    'password': 'root',
    'host': 'localhost',
    'port': 5432
}

NUM_USERS = 1_000_000               # total users to generate
WORKER_COUNT = max(1, cpu_count() - 1)  # leave one core for DB I/O
ROWS_PER_FILE = 50_000              # rows per CSV file produced per worker chunk
TEMP_DIR = os.path.join(tempfile.gettempdir(), "fintrack_populate")

# Keep deterministic-ish faker seeds per worker
BASE_FAKER_SEED = 42

# Shared password: reuse to avoid bcrypt cost (adjust if you want unique hashes)
SAMPLE_PASSWORD_HASH = "$2b$12$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

CURRENCIES = ['usd','kes','ngn','zar','rwf','ugx']
GENDERS = ['male','female','other']

# ---------- HELPERS ----------
def random_phone():
    country_codes = ['+254', '+234', '+27', '+250', '+256']
    number = randint(100000000, 999999999)
    return f"{choice(country_codes)}{number}"

def random_email(first, last, uid):
    domains = ['gmail.com', 'yahoo.com', 'outlook.com', 'hotmail.com']
    return f"{first.lower()}.{last.lower()}{uid}@{choice(domains)}"

def random_datetime(faker_inst, start_year=2015, end_year=2025):
    start = datetime(start_year, 1, 1)
    end = datetime(end_year, 12, 31)
    return faker_inst.date_time_between(start_date=start, end_date=end)

# ---------- WORKER: generate a CSV chunk ----------
def worker_generate_csv(args):
    """
    Worker function run in each process. Generates rows for given (start_index, count)
    and writes them to a CSV file in TEMP_DIR. Returns the filename.
    """
    start_index, count, worker_id = args
    faker = Faker()
    faker.seed_instance(BASE_FAKER_SEED + worker_id)
    os.makedirs(TEMP_DIR, exist_ok=True)
    filename = os.path.join(TEMP_DIR, f"users_{worker_id}_{start_index}_{count}.csv")

    with open(filename, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        for i in range(count):
            global_id = start_index + i   # ensure uniqueness across workers
            first = faker.first_name()
            last = faker.last_name()
            username = f"{first.lower()}.{last.lower()}{global_id}"
            email = random_email(first, last, global_id)
            phone = random_phone()
            email_verified = choice([True, False])
            phone_verified = choice([True, False])
            password_hash = SAMPLE_PASSWORD_HASH
            sir_name = faker.last_name()
            dob = faker.date_of_birth(minimum_age=18, maximum_age=70)
            gender = choice(GENDERS)
            country = faker.country()
            city = faker.city()
            occupation = faker.job()
            income_bracket = f"{randint(1000,100000)}-{randint(100001,200000)}"
            preferred_currency = choice(CURRENCIES)
            created_at = random_datetime(faker)
            updated_at = random_datetime(faker, start_year=created_at.year, end_year=2025)

            row = [
                username, email, 't' if email_verified else 'f',
                phone, 't' if phone_verified else 'f',
                password_hash,
                faker.first_name(), faker.last_name(), sir_name,
                dob.isoformat(), gender, country, city, occupation, income_bracket,
                preferred_currency, created_at.isoformat(sep=' '), updated_at.isoformat(sep=' ')
            ]
            writer.writerow(row)

    return filename

# ---------- MAIN: orchestrate generation and COPY ----------
def copy_file_to_db(conn, csv_file_path, table_name, columns):
    with conn.cursor() as cur, open(csv_file_path, "r", encoding="utf-8") as f:
        cols = sql.SQL(',').join(map(sql.Identifier, columns))
        copy_sql = sql.SQL("COPY {} ({}) FROM STDIN WITH (FORMAT csv)").format(
            sql.Identifier(table_name),
            cols
        )
        cur.copy_expert(copy_sql.as_string(conn), f)
    conn.commit()

def chunk_ranges(total, rows_per_file):
    remaining = total
    current_start = 0
    while remaining > 0:
        take = min(rows_per_file, remaining)
        yield (current_start, take)
        current_start += take
        remaining -= take

if __name__ == "__main__":
    if os.path.exists(TEMP_DIR):
        shutil.rmtree(TEMP_DIR)
    os.makedirs(TEMP_DIR, exist_ok=True)

    ranges = list(chunk_ranges(NUM_USERS, ROWS_PER_FILE))
    total_files = len(ranges)
    print(f"Generating {NUM_USERS} users into {total_files} CSV chunks using {WORKER_COUNT} workers...")

    worker_args = [(start, count, idx % WORKER_COUNT) for idx, (start, count) in enumerate(ranges)]

    generated_files = []
    with Pool(WORKER_COUNT) as pool:
        for fname in tqdm(pool.imap_unordered(worker_generate_csv, worker_args), total=len(worker_args), desc="Generating CSVs"):
            generated_files.append(fname)

    generated_files.sort()

    print("Connecting to database and importing CSV chunks via COPY...")
    conn = psycopg2.connect(**DB_CONFIG)

    users_columns = [
        'username','email','email_verified','phone_number','phone_verified',
        'password_hash','first_name','last_name','sir_name','date_of_birth','gender',
        'country','city','occupation','income_bracket','preferred_currency','created_at','updated_at'
    ]

    try:
        for csv_file in tqdm(generated_files, desc="COPY to DB"):
            copy_file_to_db(conn, csv_file, 'users', users_columns)
            os.remove(csv_file)
    finally:
        conn.close()

    try:
        if os.path.exists(TEMP_DIR) and not os.listdir(TEMP_DIR):
            os.rmdir(TEMP_DIR)
    except Exception:
        pass

    print("Done: users imported via COPY.")
