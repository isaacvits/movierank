var app = angular.module("MovieManagement", []);

// Controller Part
app
		.controller(
				"MovieController",
				function($scope, $http) {

					$scope.movies = [];

					var rateGreater = '';

					var rateLower = '';

					var yearGreater = '';

					var yearLower = '';

					var votesGreater = '';

					var votesLower = '';
					
					var next = false;
					
					var previous = false;

					var filter = '';
					var generoList = [];
					var userQuery = '';

					var shipArray = [];
					
					var qtdMovieBefore = 8;
					var qtdMaxMovie = 32;

					createFunction();
					initialMovieData();
					
					document.getElementById("buttonNext").onclick = function() {nextMovie()};

					// Tab Switch
					$('.categorias-list').on(
							'click',
							'li',
							function(event) {
								event.preventDefault();

								if ($(this).hasClass('active')) {
									// remove a class "active" ao elemento
									// clicado
									$(this).removeClass('active');
									// localiza e armazena posição do
									// elemento selecionado no array
									var pos = generoList.indexOf($(this).data(
											'value'));
									// remove elemento do array de lista de
									// genero
									generoList.splice(pos, 1);
									if(generoList.length == 0){
										generoList = [''];
									}
								} else {
									if(generoList.length == 0){
										generoList = [];
									}
									// adicionar a class "active" ao
									// elemento clicado
									$(this).addClass('active');
									// adiciona elemento no array de lista
									// de genero
									generoList.push($(this).data('value'));
								}

								// muda parametro de pesquisa
								$('#inputGroup-sizing-default').attr(
										'data-label', 'Genre: ' + generoList);

								update();
							});

					// pega valor do select para adicionar ao filtro
					$('#inputGroupSelect01').change(function(event) {
						filter = this.value;
					});

					$('#user-query').keyup(function(event) {
						userQuery = this.value;
						update();
					});

					// adiciona ship
					$('#add').click(function(event) {
						el = {
							'filter' : filter,
							'valor' : $('#query').val()
						};

						if (el.valor == '' || el == '')
							return;

						addShip(el);
						shipArray.push(el);
						$('#query').val('');
						update();
					});

					// adiciona elemento ship a DOM
					function addShip(info) {
						ship = '<span class="ship-remove"><span class="btn"><span class="filter">'
								+ info.filter
								+ '</span>: <span class="value font-weight-bold">'
								+ info.valor
								+ '</span><span class="badge badge-light removeship"><i class="fa fa-times"></i></span></span></span>';
						$('.filters').append(ship);
						createFunction();
					}

					// adiciona função para os novos chips inseridos
					function createFunction() {
						$('.ship').on(
								'click',
								'.removeship',
								function(event) {
									event.preventDefault();
									/* Act on the event */
									var filter = $(this).parent().find(
											'.filter').text();

									for (item in shipArray) {
										
										if (shipArray[item].filter == filter) {
											switch (filter) {

											case 'Rate IMDB Greater':
												rateGreater = ''
												break;
											case 'Rate IMDB Lower':
												rateLower = ''
												break;
											case 'Year Greater':
												yearGreater = ''
												break;
											case 'Year Lower':
												yearLower = ''
												break;
											case 'Votes IMDB Greater':
												votesGreater = ''
												break;
											case 'Votes IMDB Lower':
												votesLower = ''
												break;
											default:
												break;
											}
											delete shipArray[item];
										}
									}

									$(this).parent().parent('.ship-remove')
											.remove();
									update();
								});
					}
					
					function setValores(){
						for (item in shipArray) {
							switch (shipArray[item].filter) {

							case 'Rate IMDB Greater':
								rateGreater = shipArray[item].valor
								break;
							case 'Rate IMDB Lower':
								rateLower = shipArray[item].valor
								break;
							case 'Year Greater':
								yearGreater = shipArray[item].valor
								break;
							case 'Year Lower':
								yearLower = shipArray[item].valor
								break;
							case 'Votes IMDB Greater':
								votesGreater = shipArray[item].valor
								break;
							case 'Votes IMDB Lower':
								votesLower = shipArray[item].valor
								break;
							default:
								break;
							}

						}
					}

					function initialMovieData() {
						$http({
							method : 'GET',
							url : '/movierank/moviesNowPlaying'
						}).then(function successCallback(response) {
							$scope.movies = response.data;
							createCards($scope.movies);
							qtdMovieBefore = 8;
							if($scope.movies.length >= qtdMovieBefore){
								document.getElementById("moreItem").className = "page-item";
								document.getElementById("buttonNext").style.backgroundColor= "#FFBB00";
								document.getElementById("buttonNext").style.borderColor= "#FFBB00";
								document.getElementById("buttonNext").style.color= "#000";
							}
							else {
								document.getElementById("moreItem").className = "page-item disabled";
								document.getElementById("buttonNext").style.backgroundColor= "#9E9E9E";
								document.getElementById("buttonNext").style.borderColor= "#9E9E9E";
								document.getElementById("buttonNext").style.color= "#666";
							}
						}, function errorCallback(response) {
							console.log(response.statusText);
						});
					}

					
					

					// gera query
					function update() {

						setValores();

						$http({
							method : 'GET',
							url : '/movierank/getListMovieDiscovery',
							params : {
								title : userQuery,
								listGenres : generoList,
								yearStart : yearGreater,
								yearEnd : yearLower,
								rateStart : rateGreater,
								rateEnd : rateLower,
								voteStart : votesGreater,
								voteEnd : votesLower,
							}
						}).then(function successCallback(response) {
							$scope.movies = response.data;
							createCards($scope.movies);
							qtdMovieBefore = 8;
							if($scope.movies.length >= qtdMovieBefore){
								document.getElementById("moreItem").className = "page-item";
								document.getElementById("buttonNext").style.backgroundColor= "#FFBB00";
								document.getElementById("buttonNext").style.borderColor= "#FFBB00";
								document.getElementById("buttonNext").style.color= "#000";
							}
							else {
								document.getElementById("moreItem").className = "page-item disabled";
								document.getElementById("buttonNext").style.backgroundColor= "#9E9E9E";
								document.getElementById("buttonNext").style.borderColor= "#9E9E9E";
								document.getElementById("buttonNext").style.color= "#666";
							}
							
					}, function errorCallback(response) {
							console.log(response.statusText);
						});

					}
					
					function nextMovie() {
						setValores();
						$http({
							method : 'GET',
							url : '/movierank/nextMovies',
							params : {
								title : userQuery,
								listGenres : generoList,
								yearStart : yearGreater,
								yearEnd : yearLower,
								rateStart : rateGreater,
								rateEnd : rateLower,
								voteStart : votesGreater,
								voteEnd : votesLower,
							}
						}).then(function successCallback(response) {
							$scope.movies = response.data;
							createCards($scope.movies);
							if($scope.movies.length > qtdMovieBefore){
								qtdMovieBefore = $scope.movies.length;
							} else {
								document.getElementById("moreItem").className = "page-item disabled";
								document.getElementById("buttonNext").style.backgroundColor= "#9E9E9E";
								document.getElementById("buttonNext").style.borderColor= "#9E9E9E";
								document.getElementById("buttonNext").style.color= "#666";
							}
							if($scope.movies.length >= qtdMaxMovie || $scope.movies.length%8 != 0 ){
								document.getElementById("moreItem").className = "page-item disabled";
								document.getElementById("buttonNext").style.backgroundColor= "#9E9E9E";
								document.getElementById("buttonNext").style.borderColor= "#9E9E9E";
								document.getElementById("buttonNext").style.color= "#666";
							}
						}, function errorCallback(response) {
							console.log(response.statusText);
						});
					}
					
					function formatarNumero(n) {
					    var n = n.toString();
					    var r = '';
					    var x = 0;

					    for (var i = n.length; i > 0; i--) {
					        r += n.substr(i - 1, 1) + (x == 2 && i != 1 ? '.' : '');
					        x = x == 2 ? 0 : x + 1;
					    }

					    return r.split('').reverse().join('');
					}

					function createCards(movies) {
						$('.cards')
								.replaceWith(
										'<div class="row justify-content-md-center mt-5 cards mb-0"></div>');
						var template = '';

						for (movie in movies) {
							template += '<div class="card-film card border-0 " style="background-image:url('
									+ '/movierank/moviePoster?tconst='
									+ movies[movie].tconst
									+ ')">'
									+ '<div class="card-header px-0 py-0">'
									+ '<span class="budge">IMDB</span>'
									+ '</div>'
									+ '<div class="card-body col">'
									+ '<div class="box">'
									+ '<p class="title">'
									+ movies[movie].title
									+ '</p>'
									+ '<p class="genres">';
							// separa generos

							template += '<a href="#">'
									+ movies[movie].listGenres + '</a> ';

							template += '</p>'
									+ '<ul class="status-list clearfix">'
									+ '<li class="year">'
									+ movies[movie].year
									+ '</li>'
									+ '<li class="votes">'
									+ formatarNumero(movies[movie].numVotes)
									+ '</li>'
									+ '<li class="rate">'
									+ movies[movie].averageRating
									+ '</li>'
									+ '</ul>'
									+ '</div>'
									+ '</div>'
									+ '<div class="card-footer p-0">'
									+ '<a href="https://www.imdb.com/title/'
									+ movies[movie].tconst
									+ '" class="btn d-block">More Information</a>'
									+ '</div>' + '</div>';
						}

						$('.cards').append(template);
					}

				});
