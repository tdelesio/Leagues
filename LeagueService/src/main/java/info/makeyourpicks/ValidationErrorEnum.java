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
	PICK_ALREADY_MADE;
}
