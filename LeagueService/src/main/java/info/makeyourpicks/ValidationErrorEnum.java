package info.makeyourpicks;

import com.delesio.exception.IValidationCode;

public enum ValidationErrorEnum implements IValidationCode {

	GAME_IS_NULL,
	TEAM_IS_NULL,
	WEEK_IS_NULL,
	LEAGUE_IS_NULL,
	PICK_IS_NULL,
	PLAYER_IS_NUll,
	UNAUTHORIZED_USER,
	GAME_HAS_ALREADY_STARTED,
	TEAM_NOT_PLAYING_IN_GAME,
	PLAYER_NOT_IN_LEAGUE,
	WEEK_IS_NOT_VALID,
	PICK_ALREADY_MADE,
	LEAGUE_NAME_IS_NULL,
	DUPLICATE_LEAGUE_NAME,
	SEASON_ID_IS_NULL,
	ADMIN_IS_NULL,
	PLAYER_NOT_FOUND,
	FAVORITE_IS_NULL,
	DOG_IS_NULL,
	GAMESTART_IS_NULL,
	TEAM_CANNOT_PLAY_ITSELF,
	PASSWORD_DOES_NOT_MATCH,
	PLAYER_IN_LEAGUE
}
