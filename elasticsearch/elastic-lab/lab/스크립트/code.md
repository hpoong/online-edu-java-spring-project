```json
GET test-used-car/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "script": {
            "script": {
              "source": "doc['price'].value >= params.min_price",
              "lang": "painless",
              "params": {
                "min_price": 2000
              }
            }
          }
        }
      ]
    }
  }
}
```

```json
GET test-used-car/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "script": {
            "script": {
              "source": """
                doc['condition'].size() > 0 && 
                doc['condition'].value == params.condition &&
                doc['transmission'].size() > 0 && 
                doc['transmission'].value == params.transmission
              """,
              "lang": "painless",
              "params": {
                "condition": "excellent",
                "transmission": "automatic"
              }
            }
          }
        }
      ]
    }
  }
}
```

```json
GET test-used-car/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "term": {
            "type": {
              "value": "pickup"
            }
          }
        }
      ]
    }
  },
  "sort": [
    {
      "_script": {
        "type": "number",
        "script": {
          "source": "(doc['posting_date'].value).toInstant().toEpochMilli()"
        },
        "order": "desc"
      }
    }
  ]
}
```