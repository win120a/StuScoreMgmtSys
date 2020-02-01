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