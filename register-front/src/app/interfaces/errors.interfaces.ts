export interface IError {
  error: IErrorDetail
}

export interface IErrorDetail {
  code: number,
  message: string,
  timestamp: Date,
  errors: string[]
}
