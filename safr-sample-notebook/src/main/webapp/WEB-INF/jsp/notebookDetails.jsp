<%@ include file="top.jsp"%>

<div id="content">
<form action="" method="POST">
<table>
	<tr>
		<td>Entry list</td>
	</tr>
	<tr>
		<td>
		<table border="1" />
			<tr>
				<th>Category</th>
				<th>Entry</th>
				<th>Action</th>
			</tr>
			<c:forEach var="entry" items="${notebook.entries}">
				<tr>
					<td width="20%">${entry.category}</td>
					<td width="70%">${entry.text}</td>
					<td width="10%">Edit/Delete?</td>
				</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<hr>
		</td>
	</tr>
	<tr>
		<td>Create/edit entry</td>
	</tr>
	<tr>
		<td>
		<table border="1" />
			<tr>
				<th>Category</th>
				<th>Entry</th>
				<th></th>
			</tr>
			<tr>
				<spring:bind path="currentEntry.category">				
				<td width="20%">
					<input type="text" name="category" value="<c:out value="${currentEntry.category}"/>">
				</td>
				</spring:bind>
				<spring:bind path="currentEntry.text">				
				<td width="70%">
					<input type="text" name="text" width=50 height=3 value="<c:out value="${currentEntry.text}"/>">
				</td>
				<td width="10%">
					<input type="submit" value="Save">
				</td>
				</spring:bind>		
				<input type="hidden" name="id" value="${notebook.id}"/>
			</tr>
		</table>
		</td>
	</tr>

</table>
</form>

</div>
