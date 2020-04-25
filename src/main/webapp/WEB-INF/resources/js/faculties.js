/**
 * 
 */

$('document').ready(function() {
	
	$('.table .btn-warning').on('click',function(event){
		
		event.preventDefault();
		
		var href= $(this).attr('href');
		
		$.get(href, function(faculty, status){
			$('#idEdit').val(faculty.id);
			$('#nameEdit').val(faculty.name);
		});	
		
		$('#editModal').modal();
		
	});
	
	$('.table .btn-danger').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();
		
	});
	
});