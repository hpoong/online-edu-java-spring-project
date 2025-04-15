```json
POST /_bulk
{ "index": { "_index": "my-index-1" }}
{ "title": "elasticsearch", "content": "검색 엔진" }
{ "index": { "_index": "my-index-2" }}
{ "title": "elasticsearch", "content": "분산 시스템" }
{ "index": { "_index": "my-index-1" }}
{ "title": "elasticsearch tutorial", "content": "문서 검색" }
{ "index": { "_index": "my-index-2" }}
{ "title": "elasticsearch cluster", "content": "고가용성" }
{ "index": { "_index": "my-index-1" }}
{ "title": "elasticsearch index", "content": "데이터 저장" }
{ "index": { "_index": "my-index-2" }}
{ "title": "kibana", "content": "데이터 분석" }
{ "index": { "_index": "my-index-2" }}
{ "title": "kibana", "content": "데이터 파악" }

GET my-index-*/_search
{
  "query": {
    "match": {
      "title": "elasticsearch"
    }
  }
}

GET my-index-*/_search
{
  "indices_boost": [
    {
      "my-index-1": 1.5
    },
    {
      "my-index-2": 1.9
    }
  ], 
  "query": {
    "match": {
      "title": "elasticsearch"
    }
  }
}

GET my-index-1/_msearch
{}
{ "query": { "match": { "title": "elasticsearch" }}}
{ "index": "my-index-2" }
{ "query": { "match": { "content": "데이터" }}}
```