@file:Suppress("UNCHECKED_CAST")
/*
* MIT License
*
* Copyright (c) 2022 Himanshu Singh
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.aamu.aamuandroidapp.pluck.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aamu.aamuandroidapp.pluck.PluckConfiguration
import com.aamu.aamuandroidapp.pluck.data.PluckRepository
import com.aamu.aamuandroidapp.pluck.ui.PluckViewModel

internal class PluckViewModelFactory(
    private val pluckRepository: PluckRepository,
    private val pluckUriManager: PluckUriManager,
    private val pluckConfiguration: PluckConfiguration,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PluckViewModel::class.java)) {
            PluckViewModel(this.pluckRepository, this.pluckConfiguration, this.pluckUriManager) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
