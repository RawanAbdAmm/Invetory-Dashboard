package com.example.inventorydashboard.di


import com.example.inventorydashboard.data.repo.InventoryRepoImp
import com.example.inventorydashboard.domain.repo.InventoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindInventoryRepository(impl: InventoryRepoImp): InventoryRepository
}
