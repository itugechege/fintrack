package com.fintrack.backend.repo

import com.fintrack.backend.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
}