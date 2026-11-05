function fieldAuth() {
    import(`/eova/_view/auth/app.js?v=${x.time.now()}`).then(app => app.open())
}
