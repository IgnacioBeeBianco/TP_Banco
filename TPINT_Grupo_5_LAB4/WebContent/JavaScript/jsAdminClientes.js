

// Formatear la fecha de nacimiento antes de enviar el formulario
$(document).ready(function() {
    $('#clienteForm').submit(function(event) {
        var fechaNacimientoInput = $('#fecha_nacimiento');
        var fechaNacimientoStr = fechaNacimientoInput.val();
        if (fechaNacimientoStr) {
            var fechaNacimiento = new Date(fechaNacimientoStr);
            var year = fechaNacimiento.getFullYear();
            var month = ('0' + (fechaNacimiento.getMonth() + 1)).slice(-2);
            var day = ('0' + fechaNacimiento.getDate()).slice(-2);
            var formattedDate = year + '-' + month + '-' + day;
            fechaNacimientoInput.val(formattedDate);
        }
    });

    // Manejar el cambio de provincia para actualizar las localidades
    $('#provincia').change(function() {
        getLocalidades();
    });

    function getLocalidades() {
        var provinciaValue = $('#provincia').val();
        $.ajax({
            url: '/TPINT_Grupo_5_LAB4/ServletUbicaciones',
            method: 'GET',
            data: { localidades: provinciaValue },
            success: function(localidades) {
                $('#localidad').html(localidades);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching localidades:', error);
            }
        });
    }
    
    $('#btnCancelarCliente').click(function(event) {
        event.preventDefault();

        $.ajax({
            url: '/TPINT_Grupo_5_LAB4/ServletInfoUsuarios',
            method: 'POST',
            data: {
                btnCancelar: true 
            },
            success: function(response) {
                window.location.href = '/TPINT_Grupo_5_LAB4/Inicio_Cliente.jsp';
            },
            error: function(xhr, status, error) {
                console.error('Error al cancelar:', error);
            }
        });

        $('#formAgregarCliente')[0].reset();
    });


    // Manejar el botón de cancelar
    $('#btnCancelar').click(function(event) {
        event.preventDefault();

        $.ajax({
            url: '/TPINT_Grupo_5_LAB4/ServletInfoUsuarios',
            method: 'POST',
            data: {
                btnCancelar: true
            },
            success: function(response) {
                window.location.href = '/TPINT_Grupo_5_LAB4/ServletInfoUsuarios?Param=ListarClientes';
            },
            error: function(xhr, status, error) {
                console.error('Error al cancelar:', error);
            }
        });

        $('#formAgregarCliente')[0].reset();
    });

    // Validar que las contraseñas coincidan al enviar el formulario
    $('#formAgregar').submit(function(event) {
        var password = $('#password').val();
        var confirmPassword = $('#confirm-password').val();
        if (password !== confirmPassword) {
            event.preventDefault();
            alert("Contraseñas no coinciden.");
        }
    });
});



$(document).ready(function() {
	$('#table_id').DataTable();
});

// Modal Password

function showModal(clientName, clientUser, currentPassword) {
	$('#modalClientName').text(clientName);
	$('#modalClientUser').val(clientUser);
	$('#currentPassword').val(currentPassword);

	// Mostrar la contraseña actual temporalmente
	$('#newPassword').attr('type', 'text');
	$('#newPassword').val(currentPassword);

	$('#passwordModal').css('display', 'block');
}

function closeModal() {
	// Restaurar el tipo de campo a contraseña cuando se cierra el modal
	$('#newPassword').attr('type', 'password');
	$('#newPassword').val('');

	$('#passwordModal').css('display', 'none');
}

function submitForm() {
	$.post("ServletUsuarios", $('#passwordForm').serialize(),
			function(response) {
				if (response.trim() === "Success") {
					localStorage.setItem('passwordChangeSuccess', 'true');
					closeModal();
					location.reload();
				} else {
					$('#errorMessage').fadeIn().delay(3000).fadeOut();
					closeModal();
				}
			});
}
$(document).ready(function() {
	if (localStorage.getItem('passwordChangeSuccess') === 'true') {
		$('#confirmationMessage').fadeIn().delay(3000).fadeOut();
		localStorage.removeItem('passwordChangeSuccess');
	}
});

// Modal Cliente

function showClienteModal(dni, usuario, cuil, nombre, apellido, sexo,
		nacionalidad, fecha_nacimiento, provincia, localidad, direccion,
		correo, telefono1, telefono2) {
	$('#edit_dni').val(dni);
	$('#edit_usuario').val(usuario);
	$('#edit_cuil').val(cuil);
	$('#edit_nombre').val(nombre);
	$('#edit_apellido').val(apellido);
	$('#edit_sexo').val(sexo);
	$('#edit_nacionalidad').val(nacionalidad).change();
	$('#edit_fecha_nacimiento').val(fecha_nacimiento);
	$('#edit_provincia').val(provincia).change();
	setTimeout(function() {
		$('#edit_localidad').val(localidad).change();
	}, 500);
	$('#edit_direccion').val(direccion);
	$('#edit_correo').val(correo);
	$('#edit_telefono1').val(telefono1);
	if (telefono2 === "null") {
		$('#edit_telefono2').val('');
	} else {
		$('#edit_telefono2').val(telefono2);
	}

	$('#clienteModal').css('display', 'block');
}

function closeClienteModal() {
	$('#clienteModal').css('display', 'none');
}

window.onclick = function(event) {
	var modal = document.getElementById('clienteModal');
	if (event.target == modal) {
		modal.style.display = 'none';
	}
}

//	 

$(document).ready(function() {
	$('#edit_provincia').change(function() {
		getLocalidades();
	});

	function getLocalidades() {
		var provinciaValue = $('#edit_provincia').val();
		$.ajax({
			url : '/TPINT_Grupo_5_LAB4/ServletUbicaciones',
			method : 'GET',
			data : {
				localidades : provinciaValue
			},
			success : function(localidades) {
				$('#edit_localidad').html(localidades);
			},
			error : function(xhr, status, error) {
				console.error('Error fetching localidades:', error);
			}
		});
	}
});

// Confirmacion

function confirmarEliminacion() {
	return confirm('¿Estás seguro de que quieres eliminar este Usuario?');
}

function confirmarEdicionDatos() {
	var ok = confirm('¿Estás seguro de que quieres editar este Usuario?');
	if (ok) {
		return true;
	} else {
		closeClienteModal();
		return false;
	}

}

function confirmarEdicionContrasenia() {
	var ok = confirm('¿Estás seguro de que quieres editar esta contraseña?');
	if (ok) {
		submitForm();
		return;
	} else {
		closeModal();
		return;
	}
}

function confirmarAsignar_QuitarRol() {
	var ok = confirm('¿Estás seguro de que quieres asignar/quitar el rol administrador?');
	if (ok) {
		submitAdminForm();
		return;
	} else {
		closeAdminModal();
		return;
	}

}

// Modal Admin
function showModalAdmin(clientName, clientUser, isAdmin) {
	document.getElementById('modalAdminClientName').innerText = clientName;
	document.getElementById('modalAdminClientUser').value = clientUser;

	const adminRolSelect = document.getElementById('adminRol');
	adminRolSelect.innerHTML = '';

	if (isAdmin) {
		adminRolSelect.innerHTML = '<option value="false">Quitar</option>';
	} else {
		adminRolSelect.innerHTML = '<option value="true">Asignar</option>';
	}

	document.getElementById('adminRolModal').style.display = 'block';
}

function closeAdminModal() {
	document.getElementById('adminRolModal').style.display = 'none';
}

function submitAdminForm() {
	$.post( "ServletInfoUsuarios", $('#adminRolForm').serialize(),
					function(response) {
						if (response.trim() === "success") {
							localStorage.setItem('adminRolChangeSuccess',
									'true');
							closeAdminModal();
							location.reload();
						} else if (response.trim() === "No puedes quitarte a ti mismo el rol de administrador.") {
							$('#errorMessageAdmin').text(response).fadeIn()
									.delay(3000).fadeOut();
						} else {
							$('#errorMessageAdmin')
									.text(
											"Ocurrió un error al asignar/quitar el rol de administrador")
									.fadeIn().delay(3000).fadeOut();
						}
						closeAdminModal();
					}).fail(
					function() {
						$('#errorMessageAdmin').text(
								"Error al comunicarse con el servidor.")
								.fadeIn().delay(3000).fadeOut();
						closeAdminModal();
					});
}

$(document).ready(function() {
	if (localStorage.getItem('adminRolChangeSuccess') === 'true') {
		$('#confirmationMessageAdmin').fadeIn().delay(3000).fadeOut();
		localStorage.removeItem('adminRolChangeSuccess');
	}
});

// Controlar el EDIT MODAL USUARIO
document.addEventListener(
	'DOMContentLoaded',
	function() {
		function isNumberKey(evt) {
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if (charCode > 31 && (charCode < 48 || charCode > 57))
				return false;
			return true;
		}

		function isLetterKey(evt) {
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if ((charCode >= 65 && charCode <= 90) || // A-Z
			(charCode >= 97 && charCode <= 122) || // a-z
			(charCode >= 192 && charCode <= 246) || // acentos
			(charCode >= 248 && charCode <= 255) || // acentos
			charCode === 32) { // Space
				return true;
			}
			return false;
		}

		function validateForm() {
			var telefono1 = document.getElementById('edit_telefono1').value.trim();
			var telefono2 = document.getElementById('edit_telefono2').value.trim();
			var nombre = document.getElementById('edit_nombre').value.trim();
			var apellido = document.getElementById('edit_apellido').value.trim();

			var nameRegex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/; // Permitir
															// letras
															// y
															// caracteres
															// especiales
															// para
															// nombres
			var numberRegex = /^\d+$/; // Permitir solo números, no
										// vacío

			if (!validateLength(telefono1, 10) || !numberRegex.test(telefono1)) {
				alert('El teléfono principal debe tener exactamente 10 dígitos numéricos.');
				return false;
			}

			if (telefono2 !== '' && (!validateLength(telefono2, 10) || !numberRegex.test(telefono2))) {
				alert('El teléfono secundario debe tener exactamente 10 dígitos numéricos.');
				return false;
			}

			if (!nameRegex.test(nombre)) {
				alert('El nombre solo puede contener letras.');
				return false;
			}

			if (!nameRegex.test(apellido)) {
				alert('El apellido solo puede contener letras.');
				return false;
			}

			return true;
		}

		function validateFormAgregar() {
			var telefono1 = document.getElementById('telefono1').value.trim();
			var telefono2 = document.getElementById('telefono2').value.trim();
			var nombre = document.getElementById('nombre').value.trim();
			var apellido = document.getElementById('apellido').value.trim();

			var nameRegex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/; // Permitir letras y caracteres
															// especiales para nombres
			var numberRegex = /^\d+$/; // Permitir solo números, no vacío

			if (!validateLength(telefono1, 10) || !numberRegex.test(telefono1)) {
				alert('El teléfono principal debe tener exactamente 10 dígitos numéricos.');
				return false;
			}

			if (telefono2 !== '' && (!validateLength(telefono2, 10) || !numberRegex.test(telefono2))) {
				alert('El teléfono secundario debe tener exactamente 10 dígitos numéricos.');
				return false;
			}

			if (!nameRegex.test(nombre)) {
				alert('El nombre solo puede contener letras.');
				return false;
			}

			if (!nameRegex.test(apellido)) {
				alert('El apellido solo puede contener letras.');
				return false;
			}

			return true;
		}

		function validateLength(input, length) {
			return input.length === length;
		}

		var editForm = document.getElementById('editClienteForm')
		if (editForm) {
			document.getElementById('edit_telefono1').addEventListener('keypress', function(evt) {
				return isNumberKey(evt);
			});

			document.getElementById('edit_telefono2').addEventListener('keypress', function(evt) {
				return isNumberKey(evt);
			});

			document.getElementById('edit_nombre').addEventListener('keypress', function(evt) {
				return isLetterKey(evt);
			});

			document.getElementById('edit_apellido').addEventListener('keypress', function(evt) {
				return isLetterKey(evt);
			});

			editForm.addEventListener('submit', function(evt) {
				if (!validateForm()) {
					evt.preventDefault();
				}
			});
		}

		var formAgregar = document.getElementById('formAgregar');
		if (formAgregar) {
			formAgregar.addEventListener('submit', function(evt) {
				if (!validateFormAgregar()) {
					evt.preventDefault();
				}
			});
			document.getElementById('telefono1').addEventListener('keypress', function(evt) {
				return isNumberKey(evt);
			});

			document.getElementById('telefono2').addEventListener('keypress', function(evt) {
				return isNumberKey(evt);
			});

			document.getElementById('nombre').addEventListener('keypress', function(evt) {
				return isLetterKey(evt);
			});

			document.getElementById('apellido').addEventListener('keypress', function(evt) {
				return isLetterKey(evt);
			});
		}

	});

// Oculta/muestra la tabla de administradores
var dataTable;

function mostrarTablaAdmin() {
	var table = document.getElementById('table_idAdmin');
	var h4Admin = document.getElementById('h4_admin');
	if (table.style.display === 'none' || table.style.display === '') {
		table.style.display = 'table';
		h4Admin.style.display = 'block';
		if (!$.fn.DataTable.isDataTable('#table_idAdmin')) {
			dataTable = $('#table_idAdmin').DataTable();
		}
	} else {
		table.style.display = 'none';
		h4Admin.style.display = 'none';
		if ($.fn.DataTable.isDataTable('#table_idAdmin')) {
			dataTable.destroy();
		}
	}
}
