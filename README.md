# pcdproject
Just a school project, nothing to see here.

---

###Example usage:
```powershell
Invoke-Webrequest -Uri $host:$port -Infile "imageArchive.tar.gz" `
                                   -OutFile "gifPath.gif" `
                                   -ContentType "application/octet-stream"
```

---

###Requirements
* Java 7 or newer
* Imagick (use the config to specify path)
