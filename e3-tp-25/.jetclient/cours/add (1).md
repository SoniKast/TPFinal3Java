```toml
name = 'add (1)'
method = 'POST'
url = 'http://localhost:8080/api/cours'
sortWeight = 1000000
id = 'fbc6a8da-dee5-40c2-b2b8-bc1513006b30'

[body]
type = 'JSON'
raw = '''
{
  "nom": "3Java",
  "etudiantList": [
    { "id": 1 },
    { "id": 3 }
  ],
  "professeur": { "id": 2 }
}'''
```
