/**
 * 
 */

$('document').ready(function() {
	
	$('.table .btn-warning').on('click',function(event){
		
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(group, status){
			$('#idEdit').val(group.id);
			$('#nameEdit').val(group.name);
			$('#facultyEdit').val(group.facultyId);
		});	
		
		$('#editModal').modal();
		
	});	
});
