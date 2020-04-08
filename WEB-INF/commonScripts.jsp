<%--
    Copyright (C) 2011-2020 Andy Cheung

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
--%>

<%@ page contentType="text/html; charset=utf-8" %>

<meta name="viewport" content="width=device-width, user-scalable=no" />
<script type="text/javascript" src="<%= application.getContextPath() %>/scripts/jquery-3.4.1.js">
</script>

<script type="text/javascript">
    $(function() {
        let e = document.createElement("h6");
        e.style.textAlign = "center";
        e.innerHTML = "<br \/><br \/>Copyright (C) 2011-2020 Andy Cheung<br \/><br \/>";
        e.innerHTML += "Open source project, licensed under GNU Public License, Version 3<br \/><br />";
        e.innerHTML += "<a href='https://github.com/win120a/StuScoreMgmtSys'>GitHub Repo</a>";
        document.body.appendChild(e);
    });
</script>