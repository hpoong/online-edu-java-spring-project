
fuzzy 활용 
```json

POST my_index_typo/_bulk
{ "index" : { "_id" : "1" } }
{ "title" : "apple pie" }
{ "index" : { "_id" : "2" } }
{ "title" : "application" }
{ "index" : { "_id" : "3" } }
{ "title" : "applet" }

GET my_index_typo/_search
{
  "query": {
    "fuzzy": {
      "title": {
        "value": "appl"
      }
    }
  }
}

```

completion 활용
```json
PUT my_index
{
  "mappings": {
    "properties": {
      "title": {
        "type": "text",
        "fields": {
          "suggest": {
            "type": "completion"
          }
        }
      }
    }
  }
}

POST my_index/_bulk
{ "index" : { "_id" : "1" } }
{ "title" : "apple pie" }
{ "index" : { "_id" : "2" } }
{ "title" : "application" }
{ "index" : { "_id" : "3" } }
{ "title" : "applet" }

GET my_index/_search
{
  "suggest": {
    "title-suggest": {
      "prefix": "appe",
      "completion": {
        "field": "title.suggest",
        "fuzzy": {
          "fuzziness": 1
        }
      }
    }
  }
}

```