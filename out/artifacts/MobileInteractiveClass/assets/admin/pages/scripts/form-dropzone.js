var FormDropzone = function () {


    return {
        //main function to initiate the module
        init: function () {  

            Dropzone.options.myDropzone = {
                init: function() {
                    this.on("addedfile", function(file) {
                        // Create the remove button
                        var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>Remove servlet</button>");
                        
                        // Capture the Dropzone instance as closure.
                        var _this = this;

                        // Listen to the click event
                        removeButton.addEventListener("click", function(e) {
                          // Make sure the button click doesn't submit the form:
                          e.preventDefault();
                          e.stopPropagation();

                          // Remove the servlet preview.
                          _this.removeFile(file);
                          // If you want to the delete the servlet on the server as well,
                          // you can do the AJAX request here.
                        });

                        // Add the button to the servlet preview element.
                        file.previewElement.appendChild(removeButton);
                    });
                }            
            }
        }
    };
}();