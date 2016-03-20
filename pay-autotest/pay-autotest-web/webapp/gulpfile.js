/** * Created by Marszed on 2016/1/13. * description 自动化构建工具. * keyword * npm install gulp-sass gulp-autoprefixer gulp-minify-css gulp-jshint gulp-concat gulp-uglify gulp-imagemin gulp-notify gulp-rename gulp-livereload gulp-cache gulp-css-spriter --save-dev */var gulp = require('gulp'),	//rev = require('gulp-rev'),	//revCollector  = require('gulp-rev'),	sass = require('gulp-sass'),	autoprefixer = require('gulp-autoprefixer'),	minifycss = require('gulp-minify-css'),	jshint = require('gulp-jshint'),	uglify = require('gulp-uglify'),	imagemin = require('gulp-imagemin'),	pngquant = require('imagemin-pngquant'),	rename = require('gulp-rename'),	concat = require('gulp-concat'),	notify = require('gulp-notify'),	cache = require('gulp-cache'),	livereload = require('gulp-livereload'),	spriter = require('gulp-css-spriter'),	devConfig = 'static/dev/',	proConfig = 'static/pro/';	// Styles	gulp.task('styles', function() {		return gulp.src(devConfig+'scss/common/global.scss')			.pipe(sass())			.pipe(autoprefixer('last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))			//.pipe(gulp.dest('stylesheets'))			.pipe(rename({ suffix: '.min' }))			.pipe(minifycss())			.pipe(gulp.dest(proConfig+'css/'))			.pipe(notify({ message: 'Styles task complete' }));	});	// Scripts	gulp.task('scripts', function() {		return gulp.src(devConfig+'scripts/*/*.js')			.pipe(jshint())			.pipe(jshint.reporter('default'))			//.pipe(concat('all.js'))			.pipe(rename({ suffix: '.min' }))			.pipe(uglify())			.pipe(gulp.dest(proConfig+'scripts/'))			.pipe(notify({ message: 'Scripts task complete' }));	});	// Images	gulp.task('images', function() {		return gulp.src(devConfig+'images/*/*')			.pipe(cache(imagemin({ optimizationLevel: 3, progressive: true, interlaced: true, use: [pngquant()] })))			.pipe(gulp.dest(proConfig+'images/'))			.pipe(notify({ message: 'Images task complete' }));	});	// Css-sprite	gulp.task('sprite',function(){		return gulp.src(devConfig+'css/app.css')			.pipe(spriter({				'spriteSheet': proConfig+'images/sprites.png',				'pathToSpriteSheetFromCSS': proConfig+'images/sprites.png'			}))			.pipe(gulp.dest(proConfig+'/css/'))			.pipe(notify({ message: 'sprite task complete' }));	});	//压缩html	/*gulp.task('miniHtml', function () {		return gulp.src(['templates/frontend/layouts/main.html', 'templates/frontend/layouts/empty.html'])	 	.pipe(revCollector())	 	.pipe(notify({ message: 'miniHtml task complete'}));	});*/	// Default task	gulp.task('default', function() {		gulp.start('styles', 'scripts', 'images');	});	//scssReload	gulp.task('scssReload', function() {		gulp.src(devConfig+'scss/*/*.scss')			.pipe(livereload());	});	//imgsReload	gulp.task('imgsReload', function() {		gulp.src(devConfig+'images/*/*')			.pipe(livereload());	});	//scriptsReload	gulp.task('scriptsReload', function() {		gulp.src(devConfig+'scripts/*/*.js')			.pipe(livereload());	});	//htmlReload	gulp.task('htmlReload', function() {		gulp.src('webapp/*.html')			.pipe(livereload());	});	//default reload	gulp.task('reload', function() {		gulp.start('scssReload', 'imgsReload', 'scriptsReload', 'htmlReload');	});	//reload watch	gulp.task('watchReload',function(){		livereload.listen();		gulp.watch('webapp/*.html', ['htmlReload']);		console.log('reload watch');	});	// Watch	gulp.task('watch', function() {		gulp.watch(devConfig+'scss/*/*.scss', ['styles']);		gulp.watch(devConfig+'scripts/*/*.js', ['scripts']);		gulp.watch(devConfig+'images/*/*', ['images']);		livereload.listen();		gulp.watch(['webapp/*.html']).on('change', livereload.changed);		console.log('watch task listen file change starting reloading===>>>');	});	//Scss Watch	gulp.task('scssWatch', function(){		gulp.watch(devConfig+'scss/*/*.scss', ['styles']);	});