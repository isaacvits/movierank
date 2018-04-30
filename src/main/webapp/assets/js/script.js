
var app = angular.module("MovieManagement", []);

// Controller Part
app
		.controller(
				"MovieController",
				function($scope, $http) {

					$scope.movies = [];
					
					$scope.movieField = {
							title : "",
							listGenres : "",
							yearStart : "",
							yearEnd : "",
							rateStart : "",
							rateEnd : "",
							voteStart : "",
							voteEnd : "",
						};

					var filter = '';
					var generoList = [ 'Action' ];
					var userQuery = '';

					var shipArray = [];

					createFunction();
					initialMovieData();
				
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
								} else {
									// adicionar a class "active" ao
									// elemento clicado
									$(this).addClass('active');
									// adiciona elemento no array de lista
									// de genero
									generoList.push($(this).data('value'));
								}

								// muda parametro de pesquisa
								$('#inputGroup-sizing-default').attr(
										'data-label', 'Gênero: ' + generoList);

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
											delete shipArray[item];
										}
									}

									$(this).parent().parent('.ship-remove')
											.remove();
									update();
								});
					}

					function initialMovieData() {
						$http(
								{
									method : 'GET',
									url : 'http://localhost:8080/movierank/moviesNowPlaying'
								}).then(function successCallback(response) {
							$scope.movies = response.data;
							createCards($scope.movies);
						}, function errorCallback(response) {
							console.log(response.statusText);
						});
					}

					// gera query
					function update() {
						
						shipList = '';
						for (item in shipArray) {
							shipList += ' AND "' + shipArray[item].filter
									+ '" = "' + shipArray[item].valor + '"';
						}
						
						$http({
							method : 'GET',
							url : 'http://localhost:8080/movierank/getListMovieDiscovery',
							params : {
								title : userQuery,
								listGenres : generoList,
								yearStart : $scope.movieField.yearStart,
								yearEnd : $scope.movieField.yearEnd,
								rateStart : $scope.movieField.rateStart,
								rateEnd : $scope.movieField.rateEnd,
								voteStart : $scope.movieField.voteStart,
								voteEnd : $scope.movieField.voteEnd
							}
						}).then(function successCallback(response) {
							$scope.movies = response.data;
							createCards($scope.movies);
						}, function errorCallback(response) {
							console.log(response.statusText);
						});
						

						query = 'SELECT' + ' campos '
								+ 'FROM {TABELA} WHERE genero="' + generoList
								+ '" AND filme ="' + userQuery + '"' + shipList
								+ ';';
						$('.prompt').attr('data-query', query);
					}

					function createCards(movies) {
						$('.cards').replaceWith('<div class="row justify-content-md-center mt-5 cards mb-0"></div>');
						var template = '';

						for (movie in movies) {
							template += '<div class="card-film card border-0 " style="background-image:url('
									+ movies[movie].poster 
									+ ')">'
									+ '<div class="card-header px-0 py-0">'
									+ '<span class="budge">IMDB</span>'
									+ '</div>'
									+ '<div class="card-body col">'
									+ '<div class="box">'
									+ '<p class="title">'
									+  movies[movie].title
									+ '</p>'
									+ '<p class="genres">';
							// separa generos

							template += '<a href="#">' + movies[movie].listGenres
									+ '</a> ';

							template += '</p>'
									+ '<ul class="status-list clearfix">'
									+ '<li class="year">'
									+ movies[movie].year
									+ '</li>'
									+ '<li class="votes">'
									+ movies[movie].imdbVotes
									+ '</li>'
									+ '<li class="rate">'
									+ movies[movie].imdbRating
									+ '</li>'
									+ '</ul>'
									+ '</div>'
									+ '</div>'
									+ '<div class="card-footer p-0">'
									+ '<a href="https://www.imdb.com/title/' + movies[movie].imdbId 
									+ '" class="btn d-block">More Information</a>'
									+ '</div>' + '</div>';
						}
						
						$('.cards').append(template);
					}
				});