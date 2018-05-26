package com.sanath.cloudsms.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


/**
 * Created by sanath on 20/05/18.
 *
 * Google OAuth implementation.
 *
 * Created by following
 * https://developers.google.com/identity/sign-in/android/sign-in
 */
class GoogleAuth {

    companion object {
        const val GOOGLE_SIGN_IN = 1
    }

    private val TAG = "GoogleAuth"

    private val context: Context

    private val gso: GoogleSignInOptions
    private val mGoogleSignInClient: GoogleSignInClient

    constructor(context: Context) {

        this.context = context

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    /**
     * Given the activity reference initiate the sign in request.
     * The response will be handled in the activity's onActivityResult callback method.
     *
     */
    fun signIn(activity: Activity) {
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    /**
     * Get the existing Google Sign In account, if the user is already signed in
     * the GoogleSignInAccount will be non-null.
     */
    fun getSignedInAccount(): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>,
                                   callback: (GoogleSignInAccount?) -> Unit) {
        try {
            val account = completedTask.getResult<ApiException>(ApiException::class.java)
            callback(account)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code = " + e.statusCode)
            callback(null)
        }
    }

    fun singOut(): Boolean {
        return mGoogleSignInClient.signOut().isSuccessful
    }
}