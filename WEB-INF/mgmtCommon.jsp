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
	<style>
		table.T
		{
			border-collapse: collapse;
		}
		.T tr td
		{
			border : black 0.5px solid;
			text-align: center;
			width: 80px;
			font-size : 0.8em;
		}

		*
		{
			text-align: center;
		}
	</style>
	<script type="text/javascript">

		function checkSelection()
		{
			let objects = document.getElementsByClassName("del");
			let flag = false;

			for (var i = objects.length - 1; i >= 0; i--) {
				if (objects[i].checked)
				{
					flag = true;
					break;
				}
			}

			if (!flag || objects.length == 0)
			{
				alert("没有选择的项目！");
				return false;
			}

			if (confirm("确认删除？"))
			{
				return true;
			}

			return false;
		}
	</script>