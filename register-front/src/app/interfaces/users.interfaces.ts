export interface IUser {
  uuid: string,
  name: string,
  email: string,
}

export interface INewUser {
  name: string,
  email: string,
  password: string,
  password_confirmation: string
}
