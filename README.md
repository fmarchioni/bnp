# Progetto BNP

## Classi implementate:

### ISINFetch: Java 17 HTTP Client che recupera la lista dei codici ISINFetch eseguendo una POST request e facendo il parse dell'array di "result" contenente il codice ISIN

Uso:

```bash
java -classpath gson-2.11.0.jar;. ISINFetch.java <offset> <count>
```

Es:

```bash
# Recupera 5 ISIN partendo dall'offset 0 (defaults)
java -classpath gson-2.11.0.jar;. ISINFetch.java

# Recupera 10 ISIN partendo dall'offset 0
java -classpath gson-2.11.0.jar;. ISINFetch 0 10
```

### ISINFetchJava8: Client Java 1.8 che recupera la lista dei codici ISINFetch

Uso:

```bash
java ISINFetchJava8
```

### ISINDetails: Java 17 HTTP Client che recupera le informazioni sui certificati per un determinato ISIN

Uso:

```bash
java ISINDetails <Codice ISIN>
```
Es:

```bash
java ISINDetails NLBNPIT1LSY0
```
