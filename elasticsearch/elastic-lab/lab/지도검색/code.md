
geo_point 매핑 설정
```json
PUT test-geo-point2
{
  "mappings": {
    "properties": {
      "my_field": {
        "type": "geo_point"
      }
    }
  }
}
```

다양한 geo_point 입력 방식 테스트
```json
PUT test-geo-point2/_doc/1
{
  "my_field": {
    "lat": 37.004643,
    "lon": 127.292388
  }
}

PUT test-geo-point2/_doc/2
{
  "my_field":  "37.004643,111.004643"
}

PUT test-geo-point2/_doc/3
{
  "my_field": "wydd887rxt7v"
}

PUT test-geo-point2/_doc/4
{
  "my_field": "POINT(127.292388 37.004643)"
}

PUT test-geo-point2/_doc/5
{
  "my_field": {
    "type": "Point",
    "coordinates": [127.292388, 37.004643]
  }
}
```

geo_distance 쿼리 검색
```json
GET test-geo-point2/_search
{
  "query": {
    "geo_distance": {
      "distance": "10km",
      "my_field": {
        "lat": 37.004643,
        "lon": 127.292388
      }
    }
  }
}
```


script 기반 거리 계산 쿼리
```json
GET test-geo-point2/_search
{
  "query": {
    "bool": {
      "filter": {
        "script": {
          "script": {
            "source": """
              def distance = doc['my_field'].arcDistance(params.lat, params.lon);
              distance <= params.distance
            """,
            "lang": "painless",
            "params": {
              "lat": 37.004643,
              "lon": 127.292388,
              "distance": 10
            }
          }
        }
      }
    }
  }
}
```