var gulp = require('gulp');
var uglify = require('gulp-uglify');
var pump = require('pump');
var concat = require('gulp-concat');


gulp.task('default', function() {
    return gulp.src('src/main/resources/static/js/*.js')
        .pipe(concat('final.js'))
        .pipe(uglify('final.js', {
            mangle: false,
            output: {
                beautify: true
            }
        }))
        .pipe(gulp.dest('src/main/resources/static/prod/'));
});