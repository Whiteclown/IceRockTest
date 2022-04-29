package com.bobrovskii.core

import android.util.Base64

object Base64Decoder {

	fun decode(text: String) =
		String(Base64.decode(text, Base64.DEFAULT))
}