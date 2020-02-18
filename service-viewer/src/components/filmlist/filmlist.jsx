import React from 'react';
import { Link } from "react-router-dom";  
import "./filmlist.css";
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';

class FilmListComponent extends React.Component {

	constructor(props, context) {
		super(props, context);
	}
	
	render() {

		return (
			<ul className="content movie-list">
				{
					this.props.movies !== undefined ? 
						this.props.movies.length <= 0 ? (<li>No movies found!</li>) : 
					(<div> 
						{
							<GridList cellHeight={270} cols={3}>
								{
									this.props.movies.map(movie => (
										<GridListTile key={movie.id}>
											<Link to={'/film?id=' + movie.id} key={movie.id}>
												<img src={movie.poster_path} alt={movie.title} />
												<GridListTileBar
													title={movie.title}
													subtitle={<span>Genres: {movie.genres}</span>}
												/>
											</Link>
										</GridListTile>
									))
								}
						  </GridList>
				  		}
					  </div>)
					  : (<li>No movies found!</li>)
				}
			</ul>
		);
	}
}

const mapStateToProps = state => {
	const foundMovies = state.movies[state.movies.length-1];
	state.movies = [];
	return {
		movies: foundMovies
	};
};
	
export default FilmListComponent;