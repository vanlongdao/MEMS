module.exports = function(grunt) {
	grunt.loadNpmTasks('grunt-ng-constant');
	
	grunt.initConfig({
	  ngconstant: {
	    options: {
	    	space: '  ',
			wrap: '"use strict";\n\n {%= __ngModule %}',
	      name: 'config',
	      dest: 'app/components/appConfig.js'
	    },
	    development: {
		  constants: {
		  	memsAppConfig: {
				api: 'http://localhost:8080/mems/rest',
				dateFormat:'dd/MM/yyyy'
			}
		  }
	  	},
		production: {
		  constants: {
			  memsAppConfig: {
				  api: 'http://192.168.1.3:8081/mems/rest',
				  dateFormat:'dd/MM/yyyy'
			  }
		  }
	 	 }
	  }
	});
	
	grunt.registerTask('server', function (target) {
	  if (target === 'dist') {
	    return grunt.task.run(['build']);
	  }

	  grunt.task.run([
	    'ngconstant:development' // ADD THIS
	  ]);
	});

	grunt.registerTask('build', [
		'ngconstant:production' // ADD THIS
	]);
}