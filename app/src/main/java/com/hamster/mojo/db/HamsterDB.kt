package com.hamster.mojo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hamster.mojo.dao.HamsterDao
import com.hamster.mojo.model.Hamster

@Database(entities = [Hamster::class], version = 1)
abstract class HamsterDB : RoomDatabase() {
    abstract fun hamsterDao(): HamsterDao
}