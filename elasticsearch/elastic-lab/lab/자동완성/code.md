## ngram

```json
PUT test-ngram
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_analyzer": {
          "tokenizer": "ngram_tokenizer"
        }
      },
      "tokenizer": {
        "ngram_tokenizer": {
          "type": "ngram",
          "min_gram": 2,
          "max_gram": 3,
          "token_chars": [
            "letter",
            "digit"
          ]
        }
      }
    }
  }
}

POST test-ngram/_analyze
{
  "analyzer": "my_analyzer",
  "text": "test 2!"
}

```



## edge ngram

```json

PUT test-edge-ngram
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_analyzer": {
          "tokenizer": "edge_ngram_tokenizer"
        }
      },
      "tokenizer": {
        "edge_ngram_tokenizer": {
          "type": "edge_ngram",
          "min_gram": 2,
          "max_gram": 3,
          "token_chars": [
            "letter",
            "digit"
          ]
        }
      }
    }
  }
}

POST test-edge-ngram/_analyze
{
  "analyzer": "my_analyzer",
  "text": "Just do it"
}

```

## Search As You Type

```json
PUT test-search-as-you-type
{
  "mappings": {
    "properties": {
      "title": {
        "type": "search_as_you_type"
      }
    }
  }
}

POST test-search-as-you-type/_doc
{
  "title": "just do it"
}

POST test-search-as-you-type/_search
{
  "query": {
    "multi_match": {
      "query": "ju",
      "type": "bool_prefix",
      "fields": [
        "title",
        "title._2gram",
        "title._3gram"
      ]
    }
  }
}

```


## Completion Suggester

```json
PUT test-completion
{
  "mappings": {
    "properties": {
      "suggest": {
        "type": "completion"
      }
    }
  }
}

POST test-completion/_doc/1
{
  "suggest": {
    "input": ["just do it", "just", "do", "it"]
  }
}

POST test-completion/_search
{
  "suggest": {
    "suggestion": {
      "prefix": "jus",
      "completion": {
        "field": "suggest"
      }
    }
  }
}

```