package com.example.smarthome

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class AddContract:ActivityResultContract<String, Command>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, AddCmd::class.java).putExtra("tokenStr", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Command {
        val data = intent?.getParcelableExtra<Command>("newCommand")
        if (resultCode == Activity.RESULT_OK && data != null){
            return data
        }

        return Command("",-1,"","")
    }
}
