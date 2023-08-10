package com.levp.twimate

import android.content.Context
import android.content.Intent
import android.net.Uri


@SuppressWarnings("MagicNumber")
object Values {
    const val minHeight = 56
    const val maxHeight = 120
}


fun Context.riskyStartTwitter() {
    val formattedTwitterAddress = "https://twitter.com/"
    val browseTwitter = Intent(Intent.ACTION_VIEW, Uri.parse(formattedTwitterAddress));
    startActivity(browseTwitter);
}