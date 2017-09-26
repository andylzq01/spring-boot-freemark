<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
你好：
<#if tbUser??> </br>
${tbUser.username}
</#if>

你使用当前的密码是:${tbUser.password}
</body>
</html>