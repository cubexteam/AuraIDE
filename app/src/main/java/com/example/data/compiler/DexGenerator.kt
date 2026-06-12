package com.example.data.compiler

import com.android.tools.r8.D8
import java.io.File

class DexGenerator {
    fun generateDex(classesDirectory: File, outputDexJar: File): Boolean {
        return try {
            val classFiles = classesDirectory.walkTopDown()
                .filter { it.extension == "class" }
                .map { it.absolutePath }
                .toList()

            if (classFiles.isEmpty()) return false

            val d8Args = mutableListOf<String>().apply {
                add("--output")
                add(outputDexJar.absolutePath)
                addAll(classFiles)
            }

            D8.main(d8Args.toTypedArray())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
