import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import {SearchFilter} from './components';
import {InfoBoard} from './components';
import 'bootstrap/dist/css/bootstrap.min.css';
import './searchbar.css';

const API = 'https://reactjs-cdp.herokuapp.com/movies?';
const SEARCH_QUERY = '&search=';
const SEARCH_BY_QUERY = '&searchBy=';

class SearchBar extends React.Component {

  constructor(props, context) {
		super(props, context);

		this.state = {
      movies: []
		};
  }

  clearInputs() {
		this.setState({ description: ''});
  }

  performSearch = event => {
    event.preventDefault();
    
    if (this.state.description) {
      fetch(API + SEARCH_QUERY + this.state.description + SEARCH_BY_QUERY + 'title')
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('Something went wrong ...');
        }
      })
      .then(data => {
          this.setState({ movies: data.data, total: data.total});
          this.state.movies = data.data; 
          this.state.total =  data.total;
          this.props.onSearchMovies(this.state);
          this.clearInputs();
      })
      .catch(error => this.setState({ error }));
    }
  }

  updateInput({ currentTarget }) {
		this.setState({ description: currentTarget.value });
	}

  render() {
    const { description } = this.state;

    return (
        <form>
          <div>
            <label>netflixroulette</label>
          </div>
          <div>
            <label>FIND MORE MOVIE</label>
            <TextField
              id="standard-full-width"
              label="Label"
              style={{ margin: 8 }}
              placeholder="Search text"
              helperText="Search text"
              fullWidth
              margin="normal"
              InputLabelProps={{shrink: true}}
              value={description}
              onChange={(...args) => this.updateInput(...args)}
            />
            
            <div className="rowC">
              <SearchFilter/>
              <div>
                <Button id="searchButton"
                        variant="contained"
                        color="primary"
                        type="submit"
                        onClick={(event) => this.performSearch(event)}>Search</Button>
              </div>
            </div>
            <div>
              <InfoBoard total={ this.state.total }/>
            </div>
          </div>
        </form>
    );
  }

}

export default SearchBar;