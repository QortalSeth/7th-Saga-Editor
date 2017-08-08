package application.enums

import application.models.Spell

enum class SpellType
{
	EMPTY, FIRE1,FIRE2,ICE1,ICE2,LASER1,LASER2,LASER3,
	F_BIRD,F_BALL,BLZRD1,BLZRD2,THUNDER1,THUNDER2,
	HPCTCHR,MPCTCHR,HEAL1,HEAL2,HEAL3,ELIXIR,REVIVE1,REVIVE2,PURIFY,
	DEFENSE1,POWER,AGILITY,FSHID,PROTECT,EXIT,
	PETRIFY,DEFENSE2,VACUUM1,VACUUM2,
	POISON,RANER,LEGUS,HAIL, MISC;

	companion object
	{
		fun getChronOrder(spell: Spell): SpellType = when (spell.gameIndex)
		{
			0    -> EMPTY
			1    -> FIRE1
			2    -> FIRE2
			3    -> ICE1
			4    -> ICE2
			5    -> LASER1
			6    -> LASER2
			7    -> LASER3
			0x0C -> F_BIRD
			0x0D -> F_BALL
			0x0E -> BLZRD1
			0x0F -> BLZRD2
			0x10 -> THUNDER1
			0x11 -> THUNDER2
			0x15 -> PETRIFY
			0x16 -> DEFENSE1
			0x17 -> DEFENSE2
			0x18 -> HEAL1
			0x19 -> HEAL2
			0x1A -> HEAL3
			0x1B -> MPCTCHR
			0x1C -> AGILITY
			0x1D -> FSHID
			0x1E -> PROTECT
			0x1F -> EXIT
			0x20 -> RANER
			0x21 -> POWER
			0x22 -> HPCTCHR
			0x23 -> ELIXIR
			0x24 -> LEGUS
			0x28 -> VACUUM1
			0x29 -> VACUUM2
			0x2D -> PURIFY
			0x2E -> REVIVE1
			0x2F -> REVIVE2
			0x37 -> POISON
			0x38 -> HAIL
			else -> MISC
		}
	}
}