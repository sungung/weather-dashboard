var gulp = require('gulp'),
    usemin = require('gulp-usemin'),
    wrap = require('gulp-wrap'),
    connect = require('gulp-connect'),
    watch = require('gulp-watch'),
    minifyCss = require('gulp-minify-css'),
    minifyJs = require('gulp-uglify'),
    concat = require('gulp-concat'),
    less = require('gulp-less'),
    rename = require('gulp-rename'),
    minifyHTML = require('gulp-minify-html');

var paths = {
    scripts: 'src/main/resources/appsources/js/**/*.*',
    styles: 'src/main/resources/appsources/less/**/*.*',
    images: 'src/main/resources/appsources/img/**/*.*',
    templates: 'src/main/resources/appsources/templates/**/*.html',
    index: 'src/main/resources/appsources/index.html',
    bower_fonts: 'src/main/resources/appsources/bower_components/**/*.{ttf,woff,eof,svg,otf,eot}',
    weather_fonts: 'src/main/resources/appsources/bower_components/weather-icons/**/*.{ttf,woff,eof,svg,otf,eot}'
};

/**
 * Handle bower components from index
 */
gulp.task('usemin', function() {
    return gulp.src(paths.index)
        .pipe(usemin({
        js: [minifyJs(), 'concat'],
        css: [minifyCss({keepSpecialComments: 0}), 'concat']
    }))
        .pipe(gulp.dest('src/main/resources/static'));
});

/**
 * Copy assets
 */
gulp.task('build-assets', ['copy-bower_fonts','copy-weather_fonts']);

gulp.task('copy-bower_fonts', function() {
    return gulp.src(paths.bower_fonts)
        .pipe(rename({
        dirname: '/fonts'
    }))
        .pipe(gulp.dest('src/main/resources/static/lib'));
});

/**
 * weather_fonts css path to be changed but it comes from bower installer
 * so create font folder rather than changing css
 */
gulp.task('copy-weather_fonts', function() {
    return gulp.src(paths.weather_fonts)
        .pipe(rename({
        dirname: '/font'
    }))
        .pipe(gulp.dest('src/main/resources/static/lib'));
});

/**
 * Handle custom files
 */
gulp.task('build-custom', ['custom-images', 'custom-js', 'custom-less', 'custom-templates']);

gulp.task('custom-images', function() {
    return gulp.src(paths.images)
        .pipe(gulp.dest('src/main/resources/static/img'));
});

gulp.task('custom-js', function() {
    return gulp.src(paths.scripts)
        .pipe(minifyJs())
        .pipe(concat('dashboard.min.js'))
        .pipe(gulp.dest('src/main/resources/static/js'));
});

gulp.task('custom-less', function() {
    return gulp.src(paths.styles)
        .pipe(less())
        .pipe(gulp.dest('src/main/resources/static/css'));
});

gulp.task('custom-templates', function() {
    return gulp.src(paths.templates)
        .pipe(minifyHTML())
        .pipe(gulp.dest('src/main/resources/static/templates'));
});

/**
 * Watch custom files
 */
gulp.task('watch', function() {
    gulp.watch([paths.images], ['custom-images']);
    gulp.watch([paths.styles], ['custom-less']);
    gulp.watch([paths.scripts], ['custom-js']);
    gulp.watch([paths.templates], ['custom-templates']);
    gulp.watch([paths.index], ['usemin']);
});

/**
 * Live reload server
 */
gulp.task('webserver', function() {
    connect.server({
        root: 'src/main/resources/static',
        livereload: true,
        port: 8888
    });
});

gulp.task('livereload', function() {
    gulp.src(['src/main/resources/static/**/*.*'])
        .pipe(watch())
        .pipe(connect.reload());
});

/**
 * Gulp tasks
 */
gulp.task('build', ['usemin', 'build-assets', 'build-custom']);
gulp.task('default', ['build', 'webserver', 'livereload', 'watch']);