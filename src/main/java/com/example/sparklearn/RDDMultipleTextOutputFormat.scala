package com.example.sparklearn

import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat

class RDDMultipleTextOutputFormat extends MultipleTextOutputFormat[Any, Any] {
  override def generateFileNameForKeyValue(key: Any, value: Any, name: String): String =
    (key + "/" + name)
}