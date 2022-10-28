package com.example.dripio.domain.repository.api

interface IRepository<Domain, IdType> {
    suspend fun find(id: IdType): Domain?
    suspend fun findAll(): List<Domain>
    suspend fun add(domain: Domain)
    suspend fun deleteById(id: IdType)
    suspend fun update(domain: Domain)
}
