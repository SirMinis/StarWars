package co.uk.androidrecruitmenttask.util.tools

import co.uk.androidrecruitmenttask.R
import co.uk.androidrecruitmenttask.data.api.ErrorResponse
import co.uk.androidrecruitmenttask.util.api.NoConnectivityException
import co.uk.androidrecruitmenttask.util.configuration.ResourceProvider
import com.google.gson.Gson
import retrofit2.HttpException

class HttpErrorProviderImpl(private val resourceProvider: ResourceProvider) : HttpErrorProvider {

    override fun getStartWarsPeopleMessage(error: Throwable): String = when (error) {
        is HttpException -> parseHttpErrorMessage(error)
        is NoConnectivityException -> resourceProvider.getString(R.string.check_connection)
        else -> resourceProvider.getString(R.string.default_error_message)
    }

    private fun parseHttpErrorMessage(error: HttpException): String {
        val errorResponse = Gson()
                .fromJson(error.response().errorBody()?.string(), ErrorResponse::class.java)
        return errorResponse.detail ?: resourceProvider.getString(R.string.default_error_message)
    }
}