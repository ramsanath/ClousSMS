package com.sanath.cloudsms.commons

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanath.cloudsms.models.GoogleUserAccount
import com.sanath.smswrapper.models.Sms


/**
 * Created by sanath on 22/05/18.
 */
class Helper {

    companion object {
        fun getDrawable(context: Context, resource: Int): Drawable {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.getDrawable(resource)
            } else {
                return context.resources.getDrawable(resource)
            }
        }

        /**
         * The data in the form of Intent received at the onActivityResult when using google OAuth login
         * is returned as a DTO.
         */
        fun getUserFromIntent(intent: Intent): GoogleUserAccount {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            return try {
                val account: GoogleSignInAccount = task.getResult<ApiException>(ApiException::class.java)

                val id = account.id
                val name = if (account.givenName == null) account.displayName else account.givenName
                val email = account.email
                val photoUrl = account.photoUrl.toString()

                GoogleUserAccount(name, email, photoUrl);

            } catch (e: ApiException) {
                throw e
            }
        }

        inline fun <reified T> Gson.fromJson(json: String) =
                this.fromJson<T>(json, object : TypeToken<T>() {}.type)

        fun putMessageArgs(fragment: Fragment, message: List<Sms>) {
            val type = object : TypeToken<List<Sms>>() {}.type
            val bundle = Bundle()
            val messagesJson = Gson().toJson(message, type)
            bundle.putString(Constants.MESSAGE_LIST.name, messagesJson)
            fragment.arguments = bundle
        }

        fun getMessageArgs(fragment: Fragment): List<Sms> {
            val messagesJson: String = fragment.arguments.getString(Constants.MESSAGE_LIST.name)
            return Gson().fromJson<List<Sms>>(messagesJson)
        }
    }
}