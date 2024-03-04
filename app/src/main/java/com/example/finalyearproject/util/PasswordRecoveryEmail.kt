package com.example.finalyearproject.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage

class PasswordRecoveryEmail(
    private var to     : String  = "",
    private var subject: String  = "",
    private var content: String  = "",
    private var isHtml : Boolean = false,
) {

//    private val username = "juliusseizure45@gmail.com"
    private val username = "junkang.cjk@gmail.com"
//    private val password = "winter255aa2"
    private val password = "ctwy agdj nlpt gdeh"
    private val personal = "EVENT" //Sender name

    private val host = "smtp.gmail.com"
    private val port = "587"


    private val from = "$personal<$username>"
    private var message: MimeMessage? = null

    fun to(to: String): PasswordRecoveryEmail {
        this.to = to
        return this
    }

    fun subject(subject: String): PasswordRecoveryEmail {
        this.subject = subject
        return this
    }

    fun content(content: String): PasswordRecoveryEmail {
        this.content = content
        return this
    }

    fun isHtml(isHtml: Boolean = true): PasswordRecoveryEmail {
        this.isHtml = isHtml
        return this
    }

    private fun getMessage(): MimeMessage {
        if (message == null) {
            val prop = Properties()
            prop["mail.smtp.host"] = host
            prop["mail.smtp.port"] = port
            prop["mail.smtp.starttls.enable"] = "true"
            prop["mail.smtp.auth"] = "true"

            val auth = object : Authenticator() {
                override fun getPasswordAuthentication() = PasswordAuthentication(username, password)
            }

            val sess = Session.getDefaultInstance(prop, auth)

            message = MimeMessage(sess)
        }

        return message!!
    }

    fun send(callback: () -> Unit = {}) {
        val type = if (isHtml) "text/html;charset=utf-8" else "text/plain;charset=utf-8"

        val msg = getMessage()
        msg.setFrom(from)
        msg.setRecipients(Message.RecipientType.TO, to)
        msg.setSubject(subject)
        msg.setContent(content, type)

        CoroutineScope(Dispatchers.IO).launch {
            // NOTE: Use try-catch-finally block to silent runtime error
            Transport.send(msg)
            withContext(Dispatchers.Main) { callback() }
        }
    }
}