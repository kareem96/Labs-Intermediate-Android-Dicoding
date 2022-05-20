package com.udinus.storyapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udinus.storyapp.data.local.entity.RemoteKeys
import com.udinus.storyapp.data.local.entity.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}