var gulp = require('gulp');
var uglify = require('gulp-uglify');
var pump = require('pump');
var concat = require('gulp-concat');


gulp.task('default', function() {
    return gulp.src('src/main/resources/static/js/*.js')
        .pipe(concat('app.js'))
        .pipe(uglify('app.js', {
                    mangle: false,
                    output: {
                        beautify: true
                    }
                }))
        .pipe(gulp.dest('src/main/resources/static/prod/'));
});

//gulp.task('default');
// gulp.task('default', function (cb) {
//   pump([
//         gulp.src('src/main/resources/static/js/*.js'),
//         uglify(),
//         gulp.dest('src/main/resources/static/prod/')
//     ],
//     cb
//   );
// });

// gulp.task('default', function() {
//     gulp.src('src/main/resources/static/js/*.js')
//         .pipe(uglify('app.js', {
//             mangle: false,
//             output: {
//                 beautify: true
//             }
//         }))
//         .pipe(gulp.dest('src/main/resources/static/prod/'))
// });

// gulp.task('default', function(){
//     gulp.src('src/main/resources/static/js/*.js')
//         .pipe(concat(path.MINIFIED_OUT))
//         .pipe(uglify())
//         .pipe('src/main/resources/static/prod/');
// });
