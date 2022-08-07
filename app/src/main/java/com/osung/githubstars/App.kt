package com.osung.githubstars

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()

const val PAGE = 1
const val PAGE_SIZE = 100