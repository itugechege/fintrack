package com.fintrack.backend.repo

import com.fintrack.backend.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long>{
}