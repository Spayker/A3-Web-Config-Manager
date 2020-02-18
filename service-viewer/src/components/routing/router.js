import React from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import Main from '../main/index.js'
import DetailedMovie from '../movie/index.js'

const Router = () => {
  return (
    <Switch>
      <Route exact path="/" component={Main} />
      <Route path="/film" component={DetailedMovie} />

      <Redirect to="/not-found" />
    </Switch>
  )
}

export default Router
