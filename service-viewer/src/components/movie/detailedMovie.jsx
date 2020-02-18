import React from 'react'
import queryString from 'query-string'

const API = 'https://reactjs-cdp.herokuapp.com/movies/';

class DetailedMovie extends React.Component {

  constructor(props) {
    super(props)
  }

  state = {
    movie: {},
    canRender: false  
  }

  getDetailedMovie(movieID) {
    return new Promise((resolve, reject) => {
      fetch(API + movieID)
        .then(res => res.json())
        .then(json => {
          resolve(json)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  loadMovie = () => {
    const { id } = queryString.parse(this.props.location.search)
    this.getDetailedMovie(id).then(m => {
      this.setState({ movie: m }, () => {
        this.setState({ canRender: true })
      })
    })
  }

  componentDidMount() {
    this.loadMovie()
  }

  render() {
    const { movie } = this.state
    if (this.state.canRender) {
      return (
        <div>
          <h1>{movie.title}</h1>
          <img
            alt="Poster Image"
            src={movie.poster_path}
          />
          <h2>Release date: {movie.release_date}</h2>
          <h2>Revenue: {movie.revenue} $</h2>
          <p>
            {
              movie.genres.map(genre => (
                <span key={movie.id + genre.id}> {genre.name} </span>
              ))
            }
          </p>
        </div>
      )
    } else return null
  }
}

export default DetailedMovie