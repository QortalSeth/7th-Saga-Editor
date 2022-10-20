package application.enums

import application.models.Spell

enum class SpellOrder
{
	EMPTY, FIRE1,FIRE2,ICE1,ICE2,LASER1,LASER2,LASER3, PLASMA,
	F_BIRD,F_BALL,BLZRD1,BLZRD2,THUNDER1,THUNDER2,
	HEAL1,HEAL2,HEAL3,ELIXIR, PURIFY, REVIVE1,REVIVE2,
	DEFENSE1,POWER,AGILITY,FSHID,PROTECT,
	HPCTCHR,MPCTCHR, PETRIFY,DEFENSE2,VACUUM1,VACUUM2,
	EXIT, RANER, POISON, LEGUS, HAIL,  MISC;

	companion object
	{
		fun getChronOrder(spell: Spell): SpellOrder = when (spell.gameIndex)
		{
			0    -> EMPTY
			1    -> FIRE1
			2    -> FIRE2
			3    -> ICE1
			4    -> ICE2
			5    -> LASER1
			6    -> LASER2
			7    -> LASER3
			8    -> PLASMA
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

enum class SpellType{
	AttackOne, AttackAll, Healing, Buff, Debuff, Misc, Unused;

	companion object {
		fun getTypeOrder(spell: Spell): SpellType = when (spell.gameIndex) {
			0    -> Misc         // None
			1    -> AttackOne    // FIRE1
			2    -> AttackOne    // FIRE2
			3    -> AttackOne    // ICE1
			4    -> AttackOne    // ICE2
			5    -> AttackOne    // LASER1
			6    -> AttackOne    // LASER2
			7    -> AttackOne    // LASER3
			8    -> AttackOne    // PLASMA
			0x0C -> AttackAll    // F_BIRD
			0x0D -> AttackAll    // F_BALL
			0x0E -> AttackAll    // BLZRD1
			0x0F -> AttackAll    // BLZRD2
			0x10 -> AttackAll    // THUNDER1
			0x11 -> AttackAll    // THUNDER2
			0x15 -> Debuff       // PETRIFY
			0x16 -> Buff         // DEFENSE1
			0x17 -> Debuff       // DEFENSE2
			0x18 -> Healing      // HEAL1
			0x19 -> Healing      // HEAL2
			0x1A -> Healing      // HEAL3
			0x1B -> Debuff        // MPCTCHR
			0x1C -> Buff         // AGILITY
			0x1D -> Buff         // FSHID
			0x1E -> Buff         // PROTECT
			0x1F -> Misc         // EXIT
			0x20 -> Misc         // RANER
			0x21 -> Buff         // POWER
			0x22 -> Debuff        // HPCTCHR
			0x23 -> Healing      // ELIXIR
			0x24 -> Unused         // LEGUS
			0x28 -> Debuff       // VACUUM1
			0x29 -> Debuff       // VACUUM2
			0x2D -> Healing      // PURIFY
			0x2E -> Healing      // REVIVE1
			0x2F -> Healing      // REVIVE2
			0x37 -> Unused         // POISON
			0x38 -> Unused         // HAIL
			else -> Unused
		}
	}
}