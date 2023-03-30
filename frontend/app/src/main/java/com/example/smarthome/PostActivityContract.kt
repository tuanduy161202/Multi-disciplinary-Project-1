package com.example.smarthome

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract

class PostActivityContract:ActivityResultContract<Bundle, Bundle>() {

    override fun createIntent(context: Context, input: Bundle): Intent {
        return Intent(context, EditCommand::class.java).putExtra("bun", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle {
        val data = intent?.getBundleExtra("bundle")
        if (resultCode == Activity.RESULT_OK && data != null){
            return data
        }
        return Bundle()
    }
}