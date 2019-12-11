import React from 'react';
import { Button } from 'reactstrap';
import {SearchFilter} from './components';
import 'bootstrap/dist/css/bootstrap.min.css';
import './searchbar.css';


class SearchBar extends React.PureComponent {
  render() {
    return (
      
        <form>
          <div>
            <label>netflixroulette</label>
          </div>
          <div class="form-group">
              <label>FIND MORE MOVIE</label>
              <input type="search" class="form-control" id="exampleInputEmail1" placeholder="Search"/>
          </div>
            <SearchFilter/>
            <Button type="Button" class="btn btn-primary btn-lg">SEARCH</Button>
        </form>
      
    );
  }
}

export default SearchBar;