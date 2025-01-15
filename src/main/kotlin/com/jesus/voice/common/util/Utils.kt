package com.jesus.voice.common.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

fun <A : Any> A.logger(): Lazy<Logger> = lazy { getLogger(this.javaClass) }
