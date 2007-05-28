<%@ include file="top.jsp" %>

<div id="content">
	<table>
		<tr>
			<td>Notebook list</td>
		</tr>
		<tr>
			<td>
				<hr>
			</td>
		</tr>
		<tr>
			<td>
				<table border="1"/>
					<tr>
						<th>Notebook ID</th>
						<th>Owner</th>
						<th>Action</th>
					</tr>
					<c:forEach var="notebook" items="${notebooks}">
						<tr>
							<td><a href="detail.htm?id=${notebook.id}">${notebook.id}</a></td>				
							<td>${notebook.owner.id}</td>				
							<td><a href="delete.htm?id=${notebook.id}">Delete</a></td>				
						</tr>
					</c:forEach>
				</table>
			</td>		
		</tr>
		<tr>
			<td class="buttonBar">
				<a href="create.htm">Create</a>
			</td>
		</tr>
	</table>
</DIV>

<%@ include file="bottom.jsp" %>